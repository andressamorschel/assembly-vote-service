import * as cdk from "aws-cdk-lib";
import { Construct } from "constructs";
import * as ec2 from "aws-cdk-lib/aws-ec2";
import * as ecs from "aws-cdk-lib/aws-ecs";
import * as ecs_patterns from "aws-cdk-lib/aws-ecs-patterns";
import * as ecr from "aws-cdk-lib/aws-ecr";
import * as sqs from "aws-cdk-lib/aws-sqs";
import * as iam from "aws-cdk-lib/aws-iam";

export class AssemblyVoteServiceStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, 'AssembyVoteServiceVpc', { maxAzs: 2 });

    const cluster = new ecs.Cluster(this, 'AssembyVoteServiceCluster', { vpc: vpc });

    const repository = ecr.Repository.fromRepositoryName(this, 'AssemblyRepository', 'assembly-repository');

    const taskDefinition = new ecs.FargateTaskDefinition(this, 'AssembyVoteTaskDefinition', {
      cpu: 1024,
      memoryLimitMiB: 2048
    });

    const assemblyVoteApp = taskDefinition.addContainer('AssemblyVoteApp', {
      image: ecs.ContainerImage.fromEcrRepository(repository, 'latest'),
      logging: ecs.LogDriver.awsLogs({ streamPrefix: 'assembly-vote-service' }),
      memoryReservationMiB: 1024,
      healthCheck: {
        command: ["CMD-SHELL", "curl -f http://localhost:8080/actuator/health || exit 1"],
        interval: cdk.Duration.seconds(30),
        timeout: cdk.Duration.seconds(5),
        retries: 3,
        startPeriod: cdk.Duration.seconds(60),
      }
    });

    assemblyVoteApp.addPortMappings({ containerPort: 8080, hostPort: 8080 });

    const mongo = taskDefinition.addContainer('MongoDB', {
      image: ecs.ContainerImage.fromRegistry('mongo:latest'),
      memoryReservationMiB: 1024
    });

    mongo.addPortMappings({ containerPort: 27017, hostPort: 27017 });

    const queue = new sqs.Queue(this, 'AssemblyVoteQueue', {
      queueName: 'AssemblyVoteQueue',
      visibilityTimeout: cdk.Duration.seconds(30),
      retentionPeriod: cdk.Duration.days(1)
    });

    taskDefinition.taskRole.addToPrincipalPolicy(new iam.PolicyStatement({
      actions: [
        "sqs:SendMessage",
        "sqs:GetQueueAttributes",
        "sqs:GetQueueUrl"
      ],
      resources: [queue.queueArn]
    }));

    queue.addToResourcePolicy(new iam.PolicyStatement({
      effect: iam.Effect.ALLOW,
      principals: [new iam.ArnPrincipal(taskDefinition.taskRole.roleArn)],
      actions: ["sqs:SendMessage"],
      resources: [queue.queueArn]
    }));

    taskDefinition.taskRole.addToPrincipalPolicy(new iam.PolicyStatement({
      actions: ["ssm:GetParameter", "ssm:GetParametersByPath"],
      resources: [`arn:aws:ssm:${cdk.Stack.of(this).region}:${cdk.Stack.of(this).account}:parameter/config/AssemblyVoteService/*`]
    }));

    const fargateService = new ecs_patterns.ApplicationLoadBalancedFargateService(this, 'AssemblyVoteFargateService', {
      serviceName: 'AssembyVoteService',
      cluster: cluster,
      cpu: 512,
      desiredCount: 1,
      taskDefinition: taskDefinition,
      publicLoadBalancer: true
    });

    const scaling = fargateService.service.autoScaleTaskCount({
      minCapacity: 1,
      maxCapacity: 5,
    });

    scaling.scaleOnCpuUtilization('CpuScaling', {
      targetUtilizationPercent: 80,
      scaleInCooldown: cdk.Duration.seconds(60),
      scaleOutCooldown: cdk.Duration.seconds(60),
    });
  }
}

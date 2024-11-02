import * as cdk from "aws-cdk-lib";
import { Construct } from "constructs";
import * as ec2 from "aws-cdk-lib/aws-ec2";
import * as ecs from "aws-cdk-lib/aws-ecs";
import * as ecs_patterns from "aws-cdk-lib/aws-ecs-patterns";
import * as ecr from "aws-cdk-lib/aws-ecr";

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
      memoryReservationMiB: 1024
    });

    assemblyVoteApp.addPortMappings({ containerPort: 8080, hostPort: 8080 });

    const mongo = taskDefinition.addContainer('MongoDB', {
      image: ecs.ContainerImage.fromRegistry('mongo:latest'),
      memoryReservationMiB: 1024
    });

    mongo.addPortMappings({ containerPort: 27017, hostPort: 27017 });

    new ecs_patterns.ApplicationLoadBalancedFargateService(this, 'AssemblyVoteFargateService', {
      serviceName: 'AssembyVoteService',
      cluster: cluster,
      cpu: 512,
      desiredCount: 1,
      taskDefinition: taskDefinition,
      publicLoadBalancer: true
    });
  }
}

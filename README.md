# and-another-nicholas-testing-directory-changes-nicholas

A test for all the external work that test requires

## Team

This microservice is stewarded by team-devx-new.

## Open steps after the creation of this service

This microservice is created using the Service Wizard.
Although the initial setup is done, there are still some steps that need to be done manually.

1. To allow new microservices to access the database, you must manually sync some secrets within AWS. Please follow [these instructions](https://sre.pleo.io/docs/databases/tutorials/create-postgresql-database#configuring-access-to-new-database). If this is not done, the service will fail to deploy to our environments as it won't be able to fetch the necessary secrets to connect to its database.
2. To add new client to Kerberos, refer to [this guide](https://www.notion.so/pleo/Adding-a-new-client-in-Kerberos-6cb1d9443dfa4231bd5d33d12eb2ae34?pvs=4).
3. Remove all following default properties from your repository:```@Default("<this default should be removed once actual value has been added to AWS SecretsManager>")```
4. To enable [merge queue](https://www.notion.so/pleo/Merge-Queue-5d45b3460b0e4b73b3157044a05bcacb) in GitHub, you have to go to `Settings -> Branches -> Edit (in main branch) -> Require Merge Queue`. This helps us to merge automatically created PRs more efficiently with shorter waiting time.

## How to run this service?
1. Use `oo` to authenticate in AWS and switch to the desired profile:
```shell
oo product-dev.AdministratorAccess
```

2. Start the local database with Docker (or skip this step if you already have a local database running with another tool (i.e. Datagrip):
```shell
sh ./scripts/run-database-local.sh
```

3. Start the service:
```shell
sh ./scripts/run-service-local.sh
```

> [!TIP]
> Alternatively, if you're using IntelliJ IDEA, you can select one of these two configurations, from the "Run / Debug Configurations" dropdown, to Run or Debug directly from the IDE:
> - `And Another Nicholas Testing Directory Changes Nicholas [RunLocal]`
> - `And Another Nicholas Testing Directory Changes Nicholas [RunLocal WithDatabaseInDocker]`
    >   - This will automatically start the local database in a Docker container (if it was not running already).


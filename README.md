# Calculator Demo App
Used to demonstrate the lifecycle of developing software. Automated testing, logging and monitoring are key parts of this sample project.

## 🚀 How To Run
1. Build project with gradle task `gradle build`
2. Run Gradle Task `gradle bootRun` to start the spring boot application

## New Relic
New Relic is an observability platform which can be used to monitor the application.
With New Relic's Java agent, you can track everything from performance issues to tiny errors within your code. 
Every minute the agent posts metric timeslice and event data to the New Relic user interface, where the owner of that data can sign in and use the data to see how their website is performing.

1. Run task `gradle downloadNewrelic`
2. Run task `gradle unzipNewrelic`
3. In order to configure the application to use newrelic, add following environment parameters:
   - NEW_RELIC_APP_NAME
   - NEW_RELIC_ENVIRONMENT
   - NEW_RELIC_LICENSE_KEY
   - JAVA_OPTS: "-javaagent:<path-to-newrelic>/newrelic.jar"

## Deploy to azure
An app service can be used to deploy the spring boot application to Microsoft Azure. 
1. Upload new relic files to app service via Azure CLI:
   - Upload .yml file:
   `az webapp deploy --name {{appName}} --src-path=./newrelic/newrelic.yml --type=static --target-path=apm/newrelic/newrelic.yml --resource-group={{resourceGroup}}`
   - Upload .jar file:
   `az webapp deploy --name {{appName}} --src-path=./newrelic/newrelic.jar --type=static --target-path=apm/newrelic/newrelic.jar --resource-group={{resourceGroup}}`
2. Fill out information in `azurewebapp` gradle task in `./build.gradle.kts`:
   - subscription: Azure Subscription id
   - resourceGroup: Azure Resource Group
   - appName: Name of the app service/ web app
   - pricingTier: Pricing tier of app service/ web app, e.g. B1
3. If newrelic agent should also be deployed, setAppSettings must include :
   - put("NEW_RELIC_APP_NAME", "Calculator")
   - put("NEW_RELIC_ENVIRONMENT", "production")
   - put("NEW_RELIC_LICENSE_KEY", "<license-key, see License Key in New Relic (Profile -> License Keys)>")
   - put("JAVA_OPTS", "-javaagent:/home/site/wwwroot/apm/newrelic/newrelic.jar")
4. Run task `gradle azureWebAppDeploy`

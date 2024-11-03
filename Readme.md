1. Run the application against locally running EAP 8.
```
mvn spring-boot:run
```

2. 1. Run the application against a remote EAP 8 running on OpenShift
```
export JAVA_NAMING_PROVIDER_URL=http://eap8-eap8.apps.cluster-m55vs.m55vs.sandbox2542.opentlc.com:80
mvn spring-boot:run
```
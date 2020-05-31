<h1>Description</h1>
This starter provides an abstraction to interact with marketing cloud SOAP or REST API's. Some marketing 
cloud services are provided in both REST as well as SOAP, in this case the starter will prefer
to use REST but if a particular request cannot be performed using REST then SOAP is preferred.

<h4>Why REST preferred ?</h4>
REST is by default preferred because REST servers/hosts reuse connections (keep-alive), where as SOAP server/host
closes the connection as soon as the response is served back to client.

<h4>Usage</h4>
Just add the starter to classpath and it creates bean of type `McClient.java` for you to autowire.
For example to get System status of SOAP API, construct the particular request. All Requests are present in
`Requests.java`. Then use the autowired client to execute the request as shown below

```java
final SystemStatusRequest request = Requests.systemStatusRequest()
                                            .setTenantId(INSERT_TENANT_ID_HERE)
                                            .build();
client.execute(request); // client auto-wired
```

Note that one advantage of using this starter is that it does not bind to a single tenant. Instead all
the API's will let you choose the `tenantId` that you want to send request to. As you would assume, this starter will
starter takes care of caching different tenant tokens and refreshes (lazily when a request is made to that tenant)
 a new token when they expired.
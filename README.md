# Spring AI: Secured Tools

This is a simple example of using Spring Security's method-level security to
secure tool invocation.

## Running the example

You'll need to set the `OPENAI_API_KEY` environment variable to your OpenAI API
key. Then you can run the app as you would any Spring Boot application. For
example, using the Gradle Spring Boot plugin:

```shell
$ ./gradlew bootRun
```

Once the application is running, you can make POST requests to the "/ask" endpoint
to ask questions. The requests must be authenticated. There are two users in the
system:

 - "mickey" with password "password" and the roles "USER" and "ADMIN"
 - "donald" with password "password" and the role "USER"

You can use any HTTP client that you wish. But if you're using HTTPie, the
requests might look like this:

```shell
$ http :8080/ask question="What is the weather in Denver?" -a mickey:password -b 
{
    "answer": "The weather in Denver is Sunny with a temperature of 72°F."
}

$ http :8080/ask question="What is the weather in Denver?" -a donald:password -b
{
    "answer": "The weather in Denver is Sunny with a temperature of 72 degrees."
}

$ http :8080/ask question="Get the secret code named habuma" -a mickey:password -b
{
    "answer": "DontTellAnyone: habuma"
}

$ http :8080/ask question="Get the secret code named habuma" -a donald:password -b
{
    "answer": "Access Denied"
}
```

Notice that the first two requests are successful because the authenticated user
has the "USER" role, which is sufficient to access the "/ask" endpoint and to 
invoke the "getWeather" tool. The third request is also successful because the
authenticated user has the "ADMIN" role, which is sufficient to invoke the
"getSecret" tool. The fourth request is denied because the authenticated user
does not have the "ADMIN" role.

## The problem

I was unable to use both `@Tool` and `@PreAuthorize` in the same component class.
Doing so resulted in the methods not being registerd as tools. They may have been
secured, but they weren't registered as tools.

Therefore, to make this work, I had to break the tools classes into two separate
classes:

 - `MyTools` - Has methods annotated with `@Tool` to be registered as tools in
               Spring AI. These methods call methods in `MyToolsSecured`.
 - `MyToolsSecured` - Has methods annotated with `@PreAuthorize` to
                      be secured by Spring Security.

It works, but is a bit clumsy. It would be nice if there could only be one class
with methods that are both tools and secured.
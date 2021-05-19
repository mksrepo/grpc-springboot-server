package com.grpc.log.service;

import com.proto.greet.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.Arrays;

@GRpcService
public class GreetingService extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // extracting value from request
        Greeting greeting = request.getGreeting();
        final String firstName = greeting.getFirstName();
        final String lastName = greeting.getLastName();
        // creating response
        String result = "Hello " + firstName + " " + lastName;
        GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
        // send the response
        responseObserver.onNext(response);
        // complete the RPC call
        responseObserver.onCompleted();
    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        int[] indexes = {1, 2, 3, 4, 5};
        Arrays.stream(indexes)
                .forEach(index -> {
                    // creating response
                    Greeting greeting = request.getGreeting();
                    final String firstName = greeting.getFirstName();
                    String result = "Hello " + firstName;
                    GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder().setResult(
                            new StringBuilder()
                                    .append(greeting.getFirstName())
                                    .append(String.valueOf(index))
                                    .toString()
                    ).build();
                    responseObserver.onNext(response);
                });
    }
}

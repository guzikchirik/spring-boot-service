package com.example.demo.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bson.Document;

import com.example.demo.enums.SecurityQuestions;
import com.example.demo.models.ContactInfo;
import com.example.demo.models.PersonalInfo;
import com.example.demo.models.SecurityInfo;
import com.example.demo.models.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.mongodb.client.*;

public class MongoDbClient {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
//            createUser(mongoClient);
//            createUser(mongoClient);
//            createUser(mongoClient);

            MongoDatabase users = mongoClient.getDatabase("Users");
            MongoCollection<Document> coll = users.getCollection("Records");
            System.out.println("countDocuments = " + coll.countDocuments());
            coll.find().forEach(document -> System.out.println(document.toJson()));
        }
    }

    public static void createUser(MongoClient mongoClient) {
        UserEntity userEntity = getUserEntity();
        Document userEntityDocument = createUserEntityDocument(userEntity);
        createRecord(mongoClient, userEntityDocument);
    }

    public static void createRecord(final MongoClient client, final Document document) {
        MongoDatabase db = client.getDatabase("Users");

        db.getCollection("Records")
          .insertOne(document);
    }

//    public Document createUserEntityDocument(final UserEntity userEntity) {
//        return new Document()
//                .append("PersonalInfo", userEntity.getPersonalInfo())
//                .append("SecurityInfo", userEntity.getSecurityInfo())
//                .append("ContactInfo", userEntity.getContactInfo());
//    }

    public static Document createUserEntityDocument(final UserEntity userEntity) {
        ObjectMapper objectMapper = new ObjectMapper(); //Advisable to create and consume as bean
        try {
            return Document.parse(objectMapper.writeValueAsString(userEntity));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Document.parse("{}");
    }

    private static UserEntity getUserEntity() {
        Faker faker = new Faker(Locale.ENGLISH);
        Name name = faker.name();
        PersonalInfo personalInfo = PersonalInfo.builder()
                                                .firstName(name.firstName())
                                                .lastName(name.lastName())
                                                .dateOfBirth(faker.date()
                                                                  .birthday()
                                                                  .toString())
                                                .gender("male")
                                                .build();
        SecurityInfo securityInfo = SecurityInfo.builder()
                                                .email(faker.internet()
                                                            .emailAddress())
                                                .password(faker.internet()
                                                               .password())
                                                .securityQuestion(SecurityQuestions.FAVORITE_SPORT)
                                                .securityQuestionAnswer(faker.esports()
                                                                             .game())
                                                .build();
        Address address = faker.address();
        ContactInfo contactInfo = ContactInfo.builder()
                                             .address(address.fullAddress())
                                             .city(address.city())
                                             .state(address.state())
                                             .zipCode(address.zipCode())
                                             .phoneNumber(faker.phoneNumber()
                                                               .phoneNumber())
                                             .build();

        return UserEntity.builder()
                         .personalInfo(personalInfo)
                         .securityInfo(securityInfo)
                         .contactInfo(contactInfo)
                         .build();
    }

}

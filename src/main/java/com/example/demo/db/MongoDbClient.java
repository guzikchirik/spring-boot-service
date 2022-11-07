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
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbClient {

    public static void main(String[] args) {

//        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
//            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
//            System.out.println("databases amount is " + databases.size());
//            databases.forEach(db -> System.out.println(db.toJson()));
//        }
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            UserEntity userEntity = getUserEntity();
            Document userEntityDocument = createUserEntityDocument(userEntity);
            createRecord(mongoClient, userEntityDocument);
        }
    }

    public static void createRecord(final MongoClient client, final Document document) {
        MongoDatabase db = client.getDatabase("Users");

        db.getCollection("UsersInfo")
          .insertOne(document);
    }

    public static Document createUserEntityDocument(final UserEntity userEntity) {
        return new Document()
                .append("PersonalInfo", userEntity.getPersonalInfo())
                .append("SecurityInfo", userEntity.getSecurityInfo())
                .append("ContactInfo", userEntity.getContactInfo());
    }

    private static UserEntity getUserEntity() {
        Faker faker = new Faker(Locale.ENGLISH);
        Name name = faker.name();
        PersonalInfo personalInfo = PersonalInfo.builder()
                                                .firstName(name.firstName())
                                                .lastName(name.lastName())
                                                .dateOfBirth(faker.date().birthday().toString())
                                                .gender("male")
                                                .build();
        SecurityInfo securityInfo = SecurityInfo.builder()
                                                .email(faker.internet().emailAddress())
                                                .password(faker.internet().password())
                                                .securityQuestion(SecurityQuestions.FAVORITE_SPORT)
                                                .securityQuestionAnswer(faker.esports().game())
                                                .build();
        Address address = faker.address();
        ContactInfo contactInfo = ContactInfo.builder()
                                       .address(address.fullAddress())
                                       .city(address.city())
                                       .state(address.state())
                                       .zipCode(address.zipCode())
                                       .phoneNumber(faker.phoneNumber().phoneNumber())
                                       .build();

        return UserEntity.builder()
                         .personalInfo(personalInfo)
                         .securityInfo(securityInfo)
                         .contactInfo(contactInfo)
                         .build();
    }

}

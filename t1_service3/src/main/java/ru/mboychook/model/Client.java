package ru.mboychook.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private UUID clientUuid;

    private String firstName;

    private String lastName;

    private String middleName;

    private Boolean blockedFor;

    private String blockedWhom;
}
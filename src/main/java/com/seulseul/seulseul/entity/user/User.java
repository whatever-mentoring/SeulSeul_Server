package com.seulseul.seulseul.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID uuid;
}

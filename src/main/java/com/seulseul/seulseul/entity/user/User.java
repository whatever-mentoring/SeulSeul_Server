package com.seulseul.seulseul.entity.user;

import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.endPos.EndPos;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID uuid;
    private String token;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY) // user가 삭제되면 endPos도 삭제됨
    private List<EndPos> endPosList = new ArrayList<>();

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public User(UUID uuid, String token) {
        this.uuid = uuid;
        this.token = token;
    }

    public User(String token) {
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }

}

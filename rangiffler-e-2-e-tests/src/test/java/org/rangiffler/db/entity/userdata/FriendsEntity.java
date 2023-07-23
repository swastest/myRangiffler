package org.rangiffler.db.entity.userdata;

import lombok.*;
import org.hibernate.Hibernate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.UUID;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendsEntity {
    private UUID userId;

    private UUID friendId;

    private boolean pending;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendsEntity that = (FriendsEntity) o;
        return pending == that.pending && Objects.equals(userId, that.userId) && Objects.equals(friendId, that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId, pending);
    }
}
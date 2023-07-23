package org.rangiffler.db.entity.userdata;

import lombok.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataEntity {
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private byte[] avatar;

    public UUID convertSetId(byte[] uuidFromDb) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidFromDb);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        UUID uuid = new UUID(mostSignificantBits, leastSignificantBits);
        this.id = uuid;
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataEntity that = (UserDataEntity) o;
        return id.equals(that.id) && username.equals(that.username) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Arrays.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, firstname, lastname);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }
}

package org.rangiffler.db.entity.userdata;

import lombok.*;

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

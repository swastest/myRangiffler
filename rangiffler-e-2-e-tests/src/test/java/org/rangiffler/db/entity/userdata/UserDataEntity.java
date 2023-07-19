package org.rangiffler.db.entity.userdata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class UserDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String lastname;

    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FriendsEntity> friends = new ArrayList<>();

    @OneToMany(mappedBy = "friend", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FriendsEntity> invites = new ArrayList<>();

    public void addFriends(boolean pending, UserDataEntity... friends) {
        List<FriendsEntity> friendsEntities = Stream.of(friends)
                .map(f -> {
                    FriendsEntity fe = new FriendsEntity();
                    fe.setUser(this);
                    fe.setFriend(f);
                    fe.setPending(pending);
                    return fe;
                }).toList();

        this.friends.addAll(friendsEntities);
    }

    public void removeFriends(UserDataEntity... friends) {
        for (UserDataEntity friend : friends) {
            getFriends().removeIf(f -> f.getFriend().getId().equals(friend.getId()));
        }
    }

    public void removeInvites(UserDataEntity... invitations) {
        for (UserDataEntity invite : invitations) {
            getInvites().removeIf(i -> i.getUser().getId().equals(invite.getId()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDataEntity that = (UserDataEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

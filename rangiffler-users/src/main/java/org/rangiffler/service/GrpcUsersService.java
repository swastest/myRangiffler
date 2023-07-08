package org.rangiffler.service;

import guru.qa.grpc.rangiffler.grpc.RangifflerUsersServiceGrpc;
import guru.qa.grpc.rangiffler.grpc.User;
import guru.qa.grpc.rangiffler.grpc.UsernameRequest;
import guru.qa.grpc.rangiffler.grpc.Users;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.rangiffler.data.UserEntity;
import org.rangiffler.data.repository.UserRepository;
import org.rangiffler.ex.NotFoundException;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class GrpcUsersService extends RangifflerUsersServiceGrpc.RangifflerUsersServiceImplBase{
    private final UserRepository userRepository;

    @Autowired
    public GrpcUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getAllFriends(UsernameRequest request, StreamObserver<Users> responseObserver) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            throw new NotFoundException("Can`t find user by username: " + request.getUsername());
        }
        List<UserJson> friends = userEntity
                .getFriends()
                .stream()
                .filter(fe -> !fe.isPending())
                .map(fe -> UserJson.fromEntity(fe.getFriend(), fe.isPending()
                        ? FriendStatus.INVITATION_SENT
                        : FriendStatus.FRIEND))
                .toList();

        Users.Builder usersBuilder = Users.newBuilder();
        for (UserJson friend : friends) {
            User.Builder userBuilder = User.newBuilder()
                    .setId(friend.getId().toString())
                    .setUsername(friend.getUsername())
                    .setFirstname(friend.getFirstname())
                    .setLastname(friend.getLastname())
                    .setAvatar(friend.getAvatar());

            usersBuilder.addUsers(userBuilder.build());
        }
        Users usersResponse = usersBuilder.build();
        responseObserver.onNext(usersResponse);
        responseObserver.onCompleted();
    }
}

package org.rangiffler.db.entity.photo;

import lombok.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PhotoEntity {
    private UUID id;
    private String username;
    private String countryCode;
    private String description;
    private byte[] photo;

    public UUID convertSetId(byte[] uuidFromDb) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidFromDb);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        UUID uuid = new UUID(mostSignificantBits, leastSignificantBits);
        this.id = uuid;
        return uuid;
    }

}

package ma.tr.docnearme.modules.category;

import java.util.UUID;

public record CategoryResponse(UUID id, String name,
                               String description) {
}

@Component
public class GodzillaUserHandler {

    /**
     * {@link InventoryRepository}
     */
    private final GodzillaUserRepository new_user;

    public GodzillaUserHandler (GodzillaUserRepository new_user) {
        this.new_user = new_user;
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(GodzillaUser.class)
                .flatMap(item -> new_user.save(item.getUsername(), item.getPassword()))
                .flatMap(id -> noContent().build());
    }

}
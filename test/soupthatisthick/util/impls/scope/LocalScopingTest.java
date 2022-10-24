package soupthatisthick.util.impls.scope;

public class LocalScopingTest {

//    public enum Scope {
//        GLOBAL,
//        SCOPE_A,
//        SCOPE_B,
//        SCOPE_C,
//        SCOPE_D,
//        SCOPE_E,
//        SCOPE_F
//    }
//
//    public enum Item {
//        ITEM_1,
//        ITEM_2,
//        ITEM_3,
//        ITEM_4,
//        ITEM_5,
//        ITEM_6,
//        ITEM_7,
//        ITEM_8,
//        ITEM_9
//    }
//
//
//    private LocalScoping<Scope, Item> scopeManager;
//
//    @BeforeEach
//    public void onSetup() {
//        this.scopeManager = new LocalScoping<>(Scope.GLOBAL);
//    }
//
//    @AfterEach
//    public void onTearDown() {
//        for(Scope scope : Scope.values()) {
//            final StringBuilder sb = new StringBuilder();
//            final Set<Item> items = scopeManager.getVisibleItems(scope);
//            sb.append("" + scope + " => " + Util.describeSet(items));
//            Logger.info(sb.toString());
//        }
//    }
//
//    @Test
//    public void testSetup() {
//        assertNotNull(scopeManager);
//    }
//
//    @Test
//    public void getVisibleItems() {
//        buildTree();
//        Set<Item> items = scopeManager.getVisibleItems(Scope.SCOPE_A);
//
//        assertEquals(3, items.size());
//        assertTrue(items.contains(Item.ITEM_1));
//        assertTrue(items.contains(Item.ITEM_2));
//        assertTrue(items.contains(Item.ITEM_3));
//    }
//
//    public void buildTree() {
//        scopeManager.placeScope(Scope.GLOBAL, Scope.SCOPE_A);
//        scopeManager.placeScope(Scope.GLOBAL, Scope.SCOPE_B);
//
//        scopeManager.placeScope(Scope.SCOPE_A, Scope.SCOPE_C);
//        scopeManager.placeScope(Scope.SCOPE_A, Scope.SCOPE_D);
//        scopeManager.placeScope(Scope.SCOPE_B, Scope.SCOPE_E);
//        scopeManager.placeScope(Scope.SCOPE_B, Scope.SCOPE_F);
//
//        scopeManager.placeItem(Scope.GLOBAL, Item.ITEM_1);
//        scopeManager.placeItem(Scope.SCOPE_A, Item.ITEM_2);
//        scopeManager.placeItem(Scope.SCOPE_A, Item.ITEM_3);
//        scopeManager.placeItem(Scope.SCOPE_B, Item.ITEM_4);
//        scopeManager.placeItem(Scope.SCOPE_C, Item.ITEM_5);
//        scopeManager.placeItem(Scope.SCOPE_D, Item.ITEM_6);
//        scopeManager.placeItem(Scope.SCOPE_E, Item.ITEM_7);
//        scopeManager.placeItem(Scope.SCOPE_F, Item.ITEM_8);
//        scopeManager.placeItem(Scope.SCOPE_F, Item.ITEM_9);
//    }


}

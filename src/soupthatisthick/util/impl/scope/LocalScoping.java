package soupthatisthick.util.impl.scope;

public class LocalScoping {
//
//    public interface Scope extends Equatable {
//        boolean isVariable();
//    }
//
//    public enum ScopeType {
//        PRIVATE,
//        PUBLIC,
//        PROTECTED,
//        PACKAGE
//    }
//
//    private class SubObj {
//        private String value = "SubObj.value";
//
//        public void access() {
//        }
//    }
//
//    private class TestObj {
//        private String value = "TestObj.value";
//        private SubObj subObj = new SubObj();
//    }
//
//    public final Scope globalScope;
//    private final OneToOne<ScopeType, OneToMany<Scope, Scope>> scopeMap = new OneToOneImpl<>();
//
//    public LocalScoping(final Scope globalScope) {
//        this.globalScope = globalScope;
//        for(ScopeType scopeType : ScopeType.values()) {
//            final OneToMany<Scope, Scope> scopeTree = new OneToManyImpl<>();
//            scopeTree.addSource(globalScope);
//            scopeMap.link(scopeType, scopeTree);
//        }
//    }
//
//    /**
//     * This will return a OneToMany relationship of parent scopes to their child scopes that is
//     * visible for all to see.
//     * @param scope
//     * @return
//     */
//    private final OneToMany<Scope, Scope> getVariables(ScopeType scopeType, final Scope scope) {
//
//        final OneToMany<Scope, Scope> variables = new OneToManyImpl<>();
//        final OneToMany<Scope, Scope> scopeTree = scopeMap.getTargetOf(scopeType).get();
//        final Set<Scope> childItems = scopeTree.getSourcesOf(scope)
//            .stream()
//            .filter(s -> s.isVariable())
//            .collect(Collectors.toSet());
//        for(Scope childItem : childItems) {
//            variables.link(scope, childItem);
//        }
//
//        if (scopeType==ScopeType.PUBLIC) {
//            for(Scope childItem : childItems) {
//                final OneToMany<Scope, Scope> decendantVariables = getVariables(ScopeType.PUBLIC, childItem);
//                for(Scope decendantSource : decendantVariables.getSources()) {
//                    variables.link(childItem, decendantSource);
//                    for(Scope decendantVariable : decendantVariables.getTargetsOf(decendantSource)) {
//                        variables.link(decendantSource, decendantVariable);
//                    }
//                }
//            }
//        }
//        if (scopeType==ScopeType.PROTECTED
//            || scopeType==ScopeType.PUBLIC
//            || scopeType==ScopeType.PACKAGE) {
//            final Optional<Scope> parentScope = scopeTree.getSourceOf(scope);
//            final Set<Scope> siblingVariables = new HashSet<>();
//
//        }
//
//        return ReadOnly.oneToMany(variables);
//    }
//
//
//    public void placeScope(Scope parentScope, Scope childScope) {
//        if (globalScope.equals(childScope)) {
//            throw new IllegalArgumentException("The global scope can never have a parent!");
//        }
//        scopeTree.link(parentScope, childScope);
//    }

//    /**
//     * This will place an item within the scope tree
//     * @param scope
//     * @param property
//     */
//    private void placeItem(OneToMany<Scope, Scope> scopeTree, Scope scope, Property property) {
//        if (scope==null) {
//            scope = globalScope;
//        }
//        if (globalScope.equals(scope)) {
//            contentTree.link(scope, property);
//        } else {
//            final Optional<Scope> parentScope = scopeTree.getSourceOf(scope);
//            if (!parentScope.isPresent()) {
//                placeScope(globalScope, scope);
//            } else {
//                placeScope(parentScope.get(), scope);
//            }
//        }
//        contentTree.link(scope, property);
//    }




}

package security;

import be.objectify.deadbolt.java.cache.CompositeCache;
import be.objectify.deadbolt.java.composite.ConstraintBuilders;
import be.objectify.deadbolt.java.composite.ConstraintTree;
import be.objectify.deadbolt.java.composite.Operator;
import be.objectify.deadbolt.java.models.PatternType;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Check https://deadbolt-java.readme.io/docs/composite-constraints
 * Created by mjovel on 11-21-16.
 */
@Singleton
public class CompositeConstraints {

    @Inject
    public CompositeConstraints(final CompositeCache compositeCache,
                                final ConstraintBuilders builders)
    {
        compositeCache.register(CompositeNames.CREATE,
                new ConstraintTree(Operator.OR,
                        builders.pattern("view.all", PatternType.REGEX).build(),
                        builders.pattern("view.create",
                                PatternType.REGEX).build()));
        compositeCache.register(CompositeNames.ADMIN_ACCESS,
                new ConstraintTree(Operator.AND,
                        builders.pattern("view.all", PatternType.REGEX).build()));

//        final Constraint c1 = new ConstraintTree(// some constraints);
//        final Constraint c2 = new ConstraintTree(// some constraints);
//        final Constraint c3 = new ConstraintTree(// some constraints);
//                compositeCache.register("fooConstraint",
//                        new ConstraintTree(Operator.OR,
//                                builders.subjectNotPresent().build(),
//                                c1,
//                                c3));
//        compositeCache.register("barConstraint",
//                new ConstraintTree(Operator.AND,
//                        c2,
//                        c3));
    }
}

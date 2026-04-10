@file:Suppress("LocalVariableName")

package northburns.gw2.site.util

// Inspired by this https://github.com/reduxjs/reselect
//
// Idea here is to create a "memoized select function" for state bindings.
// Each ReSelect has two components: slicers functions and result function.
// Slicers must not allocate new memory and be pure.
// Only if all of them change from last invocation, result function is called.

// This is a very good candidate for code-generation!
// That would lead to better implementation, I think. Type safer at least.
// Much like Arrow's Tuple3, Tuple4, etc


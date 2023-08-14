package com.ms.hht.lib.fotoapparat.selector

import com.ms.hht.lib.fotoapparat.parameter.Resolution

typealias ResolutionSelector = Iterable<Resolution>.() -> Resolution?

/**
 * @return Selector function which always provides the biggest resolution.
 */
fun highestResolution(): ResolutionSelector = { maxBy(Resolution::area) }

/**
 * @return Selector function which always provides the smallest resolution.
 */
fun lowestResolution(): ResolutionSelector = { minBy(Resolution::area) }
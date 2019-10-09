package com.github.wildcardresearch.darkskyapi;

import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;

/**
 * Requested units for the {@link ForcastResponse}.
 */
public enum Units {
    // Automatically select units based on geographic location
    auto,
    // Same as si, except that windSpeed is in kilometers per hour
    ca,
    // Same as si, except that nearestStormDistance and visibility are in miles and windSpeed is in miles per hour
    uk2,
    // Imperial units (the default)
    us,
    // SI units
    si
}

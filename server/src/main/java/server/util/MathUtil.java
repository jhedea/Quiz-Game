package server.util;

public class MathUtil {
	public static float linearMap(float value, float inStart, float inEnd, float outStart, float outEnd) {
		return (((value - inStart) / (inEnd - inStart)) * (outEnd - outStart)) + outStart;
	}
}

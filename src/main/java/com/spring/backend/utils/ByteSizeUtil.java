package com.spring.backend.utils;

/**
 * 파일 용량 단위별 변환
 * @author Soorim
 *
 */
public class ByteSizeUtil {

	static final long C0 = 1L; // BYTES
    static final long C1 = C0 * 1024L; // KB
    static final long C2 = C1 * 1024L; // MB
    static final long C3 = C2 * 1024L; // GB
    static final long C4 = C3 * 1024L; // TB
    static final long C5 = C4 * 1024L; // TB

    static final long MAX = Long.MAX_VALUE;
    
    public static long bytesToKB(long size) {
        return size / (C1 / C0);
    }

    public static long bytesToMB(long size) {
        return size / (C2 / C0);
    }
    
    public static long bytesToGB(long size) {
        return size / (C3 / C0);
    }
    
    public static long bytesToTB(long size) {
        return size / (C4 / C0);
    }

    public static long bytesToPB(long size) {
        return size / (C5 / C0);
    }

    public static long kbtoBytes(long size) {
        return overflow(size, C1 / C0, MAX / (C1 / C0));
    }

    public static long kbToMB(long size) {
        return size / (C2 / C1);
    }

    public static long kbToGB(long size) {
        return size / (C3 / C1);
    }

    public static long kbToTB(long size) {
        return size / (C4 / C1);
    }

    public static long kbToPB(long size) {
        return size / (C5 / C1);
    }

    public static long mbtoBytes(long size) {
        return overflow(size, C2 / C0, MAX / (C2 / C0));
    }

    public static long mbtoKB(long size) {
        return overflow(size, C2 / C1, MAX / (C2 / C1));
    }
    
    public static long mbtoGB(long size) {
        return size / (C3 / C2);
    }

    public static long mbtoTB(long size) {
        return size / (C4 / C2);
    }
    
    public static long mbtoPB(long size) {
        return size / (C5 / C2);
    }

    public static long gbtoBytes(long size) {
        return overflow(size, C3 / C0, MAX / (C3 / C0));
    }

    public static long gbtoKB(long size) {
        return overflow(size, C3 / C1, MAX / (C3 / C1));
    }
    
    public static long gbtoMB(long size) {
        return overflow(size, C3 / C2, MAX / (C3 / C2));
    }

    public static long gbtoTB(long size) {
        return size / (C4 / C3);
    }
    
    public static long gbtoPB(long size) {
        return size / (C5 / C3);
    }
    
    /**
     * 오버플로우 체크
     * 
     * @param source
     * @param unit
     * @param over
     * @return
     */
    static long overflow(long source, long unit, long over) {
        if (source > over) return Long.MAX_VALUE;
        if (source < -over) return Long.MIN_VALUE;
        return source * unit;
    }

    public static String bytesToHuman(long size) {
    	long unit = C1;
    	if (size < unit) return size + " B";
    	int exp = (int) (Math.log(size) / Math.log(unit));
    	char pre = "KMGTPE".charAt(exp-1);
    	return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
    }
}

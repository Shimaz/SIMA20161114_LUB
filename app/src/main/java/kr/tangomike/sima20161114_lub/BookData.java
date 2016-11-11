package kr.tangomike.sima20161114_lub;

/**
 * Created by shimaz on 2016-11-11.
 */

public class BookData extends Object {

    public int bookNumber;
    public int pageCount;
    public float initZoom;
    public float minZoom;

    public BookData(int bookNum, int pageCount, float initZoom, float minZoom){
        this.bookNumber = bookNum;
        this.pageCount = pageCount;
        this.initZoom = initZoom;
        this.minZoom = minZoom;

    }

}

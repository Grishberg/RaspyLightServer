package com.grishberg.data.models;

import java.util.List;

/**
 * Created by g on 16.03.16.
 */
public class PagingResult<T> {
    private int currentPage;
    private int prevPage;
    private int nextPage;
    private int lastPage;
    private List<T> result;

    public PagingResult(int currentPage, int prevPage, int nextPage, int lastPage, List<T> result) {
        this.currentPage = currentPage;
        this.prevPage = prevPage;
        this.nextPage = nextPage;
        this.lastPage = lastPage;
        this.result = result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}


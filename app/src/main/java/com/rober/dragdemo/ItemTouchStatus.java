package com.rober.dragdemo;

public interface ItemTouchStatus {

    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemove(int position);
}
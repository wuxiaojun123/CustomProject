package com.wxj.customview.city;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54966 on 2018/6/8.
 */

public class CoordsGenerator {

	public CoordsGenerator() {
		super();
	}

	static class Coords {

		private int x, y;

		public Coords(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public static Coords copyOf(Coords c) {
			return new Coords(c.x, c.y);
		}

		@Override public String toString() {
			return String.format("(%s,%s)", this.x, this.y);
		}

		public int getX(){
			return x;
		}

		public int getY(){
			return y;
		}

	}

	private static List<Coords> generate(int d) {

		Coords[][] arr = new Coords[d][d];

		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				arr[j][i] = new Coords(j, i);
			}
		}

		List<Coords> ret = new ArrayList<>();
		for (int i = 1; i <= d; i += 2) {
			ret.addAll(readPart(arr, i));
		}

		// print(ret);
		/*
		 * calculate offset
		 */
		int offset = d / 2;
		for (Coords c : ret) {
			c.x -= offset;
			c.y -= offset;
		}

		return ret;
	}

	private static List<Coords> readPart(Coords[][] arr, int i) {
		if (i > arr.length) {
			throw new IllegalArgumentException();
		}
		List<Coords> ret = new ArrayList<>();

		int dim = arr.length;
		int minX = (dim - i) / 2;
		int maxX = minX + i - 1;
		int minY = minX;
		int maxY = maxX;
		if (i == 1) {
			addToList(ret, arr[minX][minY]);
			return ret;
		}

		int startX = maxX;
		int startY = maxY;

		while (startX > minX) {
			startX--;
			addToList(ret, arr[startX][startY]);
		}

		while (startY > minY) {
			startY--;
			addToList(ret, arr[startX][startY]);
		}

		while (startX < maxX) {
			startX++;
			addToList(ret, arr[startX][startY]);
		}

		while (startY < maxY) {
			startY++;
			addToList(ret, arr[startX][startY]);
		}

		return ret;
	}

	private static void addToList(List<Coords> ret, Coords coords) {
		ret.add(Coords.copyOf(coords));
	}

	public static void main(String[] args) {

		// print(ret);
	}

	private List<Coords> mList;

	public void init(int count) {
		mList = generate(count);
	}

	public Coords getCoords(int index) {
		return mList.get(index);
	}

	private static void print1(List<Coords> ret) {
		System.out.println("=======" + ret.size());
		for (Coords p : ret) {
			System.out.print("(" + p.x + "," + p.y + ")");
		}
		System.out.println();
	}

}

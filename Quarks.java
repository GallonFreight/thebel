
public enum Quarks {

	up(new int[][]{
		{3,12},
		{1,2},
		{},
		{1,12},
		{},
		{1,4},
		{5,12},
		{},
		{5,12},
		{3,4},
		{5,12},
		{},
		{5,12},
		{1,4},
		{},
		{1,12},
		{},
		{1,2},
		{3,12},
		{}
	}),

	down(new int[][]{
		{1, 10},
		{11, 12},
		{},
		{1, 12},
		{},
		{9, 12},
		{1, 8},
		{},
		{1, 8},
		{9, 10},
		{1, 8},
		{},
		{1, 8},
		{9, 12},
		{},
		{1, 12},
		{},
		{11, 12},
		{1, 10},
		{}
	}),

	charm(new int[][]{
		{7, 12},
		{},
		{7, 12},
		{},
		{1, 6, 7, 12},
		{},
		{1, 6, 7, 8},
		{},
		{1, 6, 7, 12},
		{},
		{7, 12},
		{},
		{7, 12},
		{}
	}),

	strange(new int[][]{
		{5, 8},
		{},
		{5, 8},
		{},
		{5, 8},
		{},
		{1, 6, 7, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 6, 7, 12},
		{},
		{5, 8},
		{},
		{5, 8},
		{},
		{5, 8},
		{}
	}),

	top(new int[][]{
		{5, 12},
		{1, 4},
		{},
		{5, 12},
		{},
		{1, 2},
		{},
		{1, 4, 5, 12},
		{},
		{1, 4},
		{5, 12},
		{1, 4},
		{5, 12},
		{1, 4},
		{},
		{1, 4, 5, 12},
		{},
		{1, 2},
		{},
		{5, 12},
		{},
		{1, 4},
		{5, 12},
		{}
	});

	private final int[][] places;

	Quarks(int[][] places){
		this.places = places;
	}
}

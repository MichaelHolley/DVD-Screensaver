let x;
let y;
let xvel;
let yvel;

function setup() {
	createCanvas(800, 600);
	x = 200;
	y = 100;
	xvel = 10;
	yvel= 10;
}

function draw() {
	background(0);
	rect(x, y, 80, 60);

	x = x + xvel;
	y = y + yvel;

	if (x + 80 == width || x == 0) {
		xvel = -xvel;
	} else if (y + 60 == height || y == 0) {
		yvel = -yvel;
	}
}

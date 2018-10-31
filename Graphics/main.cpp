#include<bits/stdc++.h>
#include <windows.h>
#include <glut.h>
using namespace std;

#define pi (2*acos(0.0))

double cameraHeight;
double cameraAngle;
int drawgrid;
int drawaxes;
double angle;

struct point
{
	double x,y;
};


void drawAxes()
{
	if(drawaxes==1)
	{
		glColor3f(1.0, 1.0, 1.0);
		glBegin(GL_LINES);{
			glVertex3f( 100,0,0);
			glVertex3f(-100,0,0);

			glVertex3f(0,-100,0);
			glVertex3f(0, 100,0);

			glVertex3f(0,0, 100);
			glVertex3f(0,0,-100);
		}glEnd();
	}
}

void drawPoints()
{
    ifstream input;
    int n;

    input.open("input.txt");
    input >> n;

    for(int i=0;i<n;i++){
        double x,y;

        input >> x >> y;

        glBegin(GL_POINTS);{
            glColor3f(1,0,0);
            glVertex3f(x,y,0);
        }
        glEnd();

    }
}

void drawRange()
{
    ifstream input;
    int n;
    //input.open("range_0.txt");
    input.open("range_1.txt");
    //input.open("range_2.txt");
    //input.open("range_3.txt");

    vector<point> points;

    for(int i=0;i<4;i++){
        double x,y;
        point p;

        input >> x >> y;
        p.x = x;
        p.y = y;
        points.push_back(p);
    }
    input.close();

    for(int i=0;i<points.size();i++){
        glBegin(GL_LINES);{
            glColor3f(0,1,0);
            glVertex3f(points[i].x,points[i].y,0);
            glVertex3f(points[(i+1)%points.size()].x,points[(i+1)%points.size()].y,0);
        }glEnd();
    }
}


void drawCircle(float cx, float cy, float r, int num_segments)
{
    glColor3f(1,0,0);
    glBegin(GL_LINE_LOOP);
    for(int ii = 0; ii < num_segments; ii++){
        float theta = 2.0f * 3.1415926f * float(ii) / float(num_segments);//get the current angle

        float x = r * cosf(theta);//calculate the x component
        float y = r * sinf(theta);//calculate the y component

        glVertex2f(x + cx, y + cy);//output vertex

    }
    glEnd();
}

void drawNN()
{
    ifstream input;
    int n;
    double x,y,r;
    input.open("NN_0.txt");
    //input.open("NN_1.txt");

    input >> x >> y >> r;
    input.close();

    glBegin(GL_POINTS);{
        glColor3f(0,1,0);
        glVertex3f(x,y,0);
    }glEnd();
    drawCircle(x,y,r,100);

}



void drawSS()
{
    drawPoints();
    //drawRange();
    drawNN();
}

void keyboardListener(unsigned char key, int x,int y){
	switch(key){

		case '1':
			drawgrid=1-drawgrid;
			break;

		default:
			break;
	}
}


void specialKeyListener(int key, int x,int y){
	switch(key){
		case GLUT_KEY_DOWN:		//down arrow key
			cameraHeight -= 3.0;
			break;
		case GLUT_KEY_UP:		// up arrow key
			cameraHeight += 3.0;
			break;

		case GLUT_KEY_RIGHT:
			cameraAngle += 0.03;
			break;
		case GLUT_KEY_LEFT:
			cameraAngle -= 0.03;
			break;

		case GLUT_KEY_PAGE_UP:
			break;
		case GLUT_KEY_PAGE_DOWN:
			break;

		case GLUT_KEY_INSERT:
			break;

		case GLUT_KEY_HOME:
			break;
		case GLUT_KEY_END:
			break;

		default:
			break;
	}
}


void mouseListener(int button, int state, int x, int y){	//x, y is the x-y of the screen (2D)
	switch(button){
		case GLUT_LEFT_BUTTON:
			if(state == GLUT_DOWN){		// 2 times?? in ONE click? -- solution is checking DOWN or UP
				drawaxes=1-drawaxes;
			}
			break;

		case GLUT_RIGHT_BUTTON:
			//........
			break;

		case GLUT_MIDDLE_BUTTON:
			//........
			break;

		default:
			break;
	}
}



void display(){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(0,0,0,0);	//color black
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);

	glLoadIdentity();
	gluLookAt(0,0,40,	0,0,0,	0,1,0);
	glMatrixMode(GL_MODELVIEW);
	//drawAxes();
    drawSS();
	glutSwapBuffers();
}


void animate(){
	angle+=0.05;
	//codes for any changes in Models, Camera
	glutPostRedisplay();
}

void init(){
	//codes for initialization
	drawgrid=0;
	drawaxes=1;
	cameraHeight=150.0;
	cameraAngle=1.0;
	angle=0;

	//clear the screen
	glClearColor(0,0,0,0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(80,	1,	1,	1000.0);

}

int main(int argc, char **argv){
	glutInit(&argc,argv);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(0, 0);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGB);	//Depth, Double buffer, RGB color

	glutCreateWindow("My OpenGL Program");

	init();

	glEnable(GL_DEPTH_TEST);	//enable Depth Testing

	glutDisplayFunc(display);	//display callback function
	glutIdleFunc(animate);		//what you want to do in the idle time (when no drawing is occuring)

	glutKeyboardFunc(keyboardListener);
	glutSpecialFunc(specialKeyListener);
	glutMouseFunc(mouseListener);

	glutMainLoop();		//The main loop of OpenGL

	return 0;
}

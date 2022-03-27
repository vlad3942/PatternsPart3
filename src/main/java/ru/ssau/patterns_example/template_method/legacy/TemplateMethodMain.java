package ru.ssau.patterns_example.template_method.legacy;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TemplateMethodMain extends Application {

    private static final int min = 0;
    private static final int max = 180;
    private static final int circleR = 10;

    private long time = 3000L;

    public int xStart = 0;
    public int yStart = 0;
    public int xFinish = 180;
    public int yFinish = 37;

    private Thread thread;

    @Override
    public void start(Stage stage) {

        Circle circle = new Circle();

        circle.setCenterX(xStart + circleR);
        circle.setCenterY(yStart + circleR);

        circle.setRadius(circleR);
        circle.setFill(Color.AQUAMARINE);
        //Настройка ширины обводки круга
        circle.setStrokeWidth(5);
        //Создание перехода
        TranslateTransition translateTransition = new TranslateTransition();
        //Настройка длительности перехода
        translateTransition.setDuration(Duration.millis(time));
        //Установка узла для перехода
        translateTransition.setNode(circle);
        //Установка значения перехода по оси x и y.
        translateTransition.setByX(xFinish);
        translateTransition.setByY(yFinish);
        //Установка количества циклов для перехода (1 по дефолту)
        //translateTransition.setCycleCount(50);

        //Установка значения автоматического реверса на false (нет смысла, false по дефолту)
//        translateTransition.setAutoReverse(false);

        //Playing the animation
        translateTransition.play();

        thread = new Thread(() -> {
            while (true) {
                this.checker(circle);
            }
        });
        thread.setDaemon(true);
        thread.start();

        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");
        canvas.setPrefSize(200, 200);
        //circle.relocate(20, 20);
        //Rectangle rectangle = new Rectangle(100,100,Color.RED);
        //rectangle.relocate(70,70);
        canvas.getChildren().addAll(circle/*, rectangle*/);

        Button buttonCircle = new Button("Пуск");
        buttonCircle.setOnAction(e -> {
            Thread thread = new Thread(new CircleMover(0, 180, canvas, circleR));
            thread.setDaemon(true);
            thread.start();
        });

        HBox hb = new HBox(10);
        hb.getChildren().addAll(new Button("Hello"), new Button("World"), buttonCircle);

        BorderPane bp = new BorderPane();

        bp.setTop(hb);

        VBox vb = new VBox(canvas, bp, new Button("Hello"));

        //Creating a Group object
        Group root = new Group(vb);

        //Creating a scene object
        Scene scene = new Scene(root, 200, 300);

        //Setting title to the Stage
        stage.setTitle("Translate transition example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

    private void checker(Circle circle) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calc();
        TranslateTransition tt = new TranslateTransition(Duration.millis(time), circle);
        tt.setByX(xFinish - xStart);
        tt.setByY(yFinish - yStart);
        tt.play();
    }


    public void calc() {
        int x_old = xStart;
        int y_old = yStart;
        int x = xFinish;
        int y = yFinish;
        if (x - x_old == 0) {
            int y_tmp = yStart;
            yStart = yFinish;
            yFinish = y_tmp;
            return;
        }
        if (y - y_old == 0) {
            int x_tmp = xStart;
            xStart = xFinish;
            xFinish = x_tmp;
            return;
        }
        //Получаем отражённый отрезок:
        int y_old_1 = -y_old + 2 * y;

        double a = 1.0d * (y - y_old_1) / (x - x_old);
        double b = y_old_1 - (1.0d * x_old * (y - y_old_1) / (x - x_old));

        //Пытаемся найти пересечения с рамкой:
        double test = a * min + b;
        int val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != val && x != min) {
                swap(min, val);
                return;
            }
        }
        test = a * max + b;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != val && x != max) {
                swap(max, val);
                return;
            }
        }
        test = (min - b) / a;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != min && x != val) {
                swap(val, min);
                return;
            }
        }
        test = (max - b) / a;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != max && x != val) {
                swap(val, max);
            }
        }
    }

    private void swap(int p1, int p2) {
        xStart = xFinish;
        xFinish = p1;
        yStart = yFinish;
        yFinish = p2;
    }
}

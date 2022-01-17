package com.ldm.ejemplojuegopiratas.juego;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.util.List;
import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Input.TouchEvent;
import com.ldm.ejemplojuegopiratas.Pantalla;
import com.ldm.ejemplojuegopiratas.androidimpl.AndroidMusica;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    //NUEVO
    int melodiaActual = 1;

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;
                    if(Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                    }else{
                        Assets.melodia1.stop();
                        Assets.melodia2.stop();
                        Assets.melodia3.stop();
                    }
                }

                //NUEVO
                if(inBounds(event, 64, g.getHeight() - 64, 64, 64)) {
                    if(melodiaActual==1){
                        melodiaActual=2;
                        Assets.melodia1.stop();
                        Assets.melodia3.stop();
                        Assets.melodia2.play();
                        Assets.melodia2.setLooping(true);
                    }else if(melodiaActual==2){
                        melodiaActual=3;
                        Assets.melodia2.stop();
                        Assets.melodia1.stop();
                        Assets.melodia3.play();
                        Assets.melodia3.setLooping(true);
                    }else if(melodiaActual==3){
                        melodiaActual=1;
                        Assets.melodia2.stop();
                        Assets.melodia3.stop();
                        Assets.melodia1.play();
                        Assets.melodia1.setLooping(true);
                    }
                }

                if(inBounds(event, 64, 220, 192, 42) ) {
                    juego.setScreen(new PantallaJuego(juego));
                    if(Configuraciones.sonidoHabilitado) {
                        Assets.melodia1.stop();
                        Assets.melodia2.stop();
                        Assets.melodia3.stop();
                        Assets.pulsar.play(1);
                    }

                    return;
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    juego.setScreen(new PantallaMaximasPuntuaciones(juego));
                    if(Configuraciones.sonidoHabilitado) {
                        Assets.melodia1.stop();
                        Assets.melodia2.stop();
                        Assets.melodia3.stop();
                        Assets.pulsar.play(1);
                    }
                    return;
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    juego.setScreen(new PantallaAyuda(juego));
                    if(Configuraciones.sonidoHabilitado) {
                        Assets.melodia1.stop();
                        Assets.melodia2.stop();
                        Assets.melodia3.stop();
                        Assets.pulsar.play(1);
                    }
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.menuprincipal, 64, 220);

        if(Configuraciones.sonidoHabilitado) {
            g.drawPixmap(Assets.botones, 0, 416, 0, 0, 64, 64);
            if (melodiaActual == 1) {
                g.drawPixmap(Assets.botonMelodia1, 64, 416, 0, 0, 64, 64);
            } else if (melodiaActual == 2) {
                g.drawPixmap(Assets.botonMelodia2, 64, 416, 0, 0, 64, 64);
            } else if (melodiaActual == 3) {
                g.drawPixmap(Assets.botonMelodia3, 64, 416, 0, 0, 64, 64);
            }
        }else {
            g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);
            Assets.melodia1.stop();
            Assets.melodia2.stop();
            Assets.melodia3.stop();
        }
    }

    @Override
    public void pause() {
        Configuraciones.save(juego.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}


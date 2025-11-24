package com.example.osu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManiaGameView extends View {

    public interface OnGameCompletionListener {
        void onGameCompleted();
    }

    private OnGameCompletionListener gameCompletionListener;

    private static final String TAG = "ManiaGameView";

    // --- Paints ---
    private Paint lanePaint, hitLinePaint, textPaint, notePaint, holdNotePaint, holdNoteActivePaint, scorePaint, comboPaint, explosionPaint, judgmentPaint;

    // --- Game Data ---
    private ChartMania chart;
    private List<NotaMania> activeNotes;
    private List<Explosion> explosions;
    private List<JudgmentText> judgmentTexts;
    private SoundManager soundManager;

    // --- Game State ---
    private long startTime = 0;
    private long pauseTime = 0; // Para manejar la pausa
    private boolean isPlaying = false;
    private int score = 0;
    private int displayScore = 0;
    private int combo = 0;
    private int maxCombo = 0;
    private int perfects = 0;
    private int greats = 0;
    private int goods = 0;
    private int misses = 0;

    private SparseBooleanArray laneHeld = new SparseBooleanArray();
    private NotaMania[] heldNotes;
    private long hitlinePulseTime = -1;

    // --- Constants ---
    private static final int PERFECT_WINDOW_MS = 40;
    private static final int GREAT_WINDOW_MS = 80;
    private static final int GOOD_WINDOW_MS = 120;
    private static final int MISS_THRESHOLD_MS = 150;

    private static final float HITLINE_POSITION_Y_PERCENT = 0.85f;
    // --- CAMBIO DE VELOCIDAD: NOTAS MÁS RÁPIDAS ---
    private static final float NOTE_TRAVEL_TIME_MS = 1500.0f;

    public ManiaGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnGameCompletionListener(OnGameCompletionListener listener) {
        this.gameCompletionListener = listener;
    }

    private void init(Context context) {
        soundManager = SoundManager.getInstance(context);
        explosions = new ArrayList<>();
        judgmentTexts = new ArrayList<>();

        lanePaint = new Paint();
        lanePaint.setColor(Color.DKGRAY);
        lanePaint.setStyle(Paint.Style.STROKE);
        lanePaint.setStrokeWidth(2);

        hitLinePaint = new Paint();
        hitLinePaint.setColor(Color.CYAN);
        hitLinePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        notePaint = new Paint();
        notePaint.setColor(Color.WHITE);
        notePaint.setStyle(Paint.Style.FILL);

        holdNotePaint = new Paint();
        holdNotePaint.setColor(Color.YELLOW);
        holdNotePaint.setStyle(Paint.Style.FILL);

        holdNoteActivePaint = new Paint();
        holdNoteActivePaint.setColor(Color.rgb(255, 255, 120));
        holdNoteActivePaint.setStyle(Paint.Style.FILL);

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(120);
        scorePaint.setTextAlign(Paint.Align.CENTER);

        comboPaint = new Paint();
        comboPaint.setColor(Color.WHITE);
        comboPaint.setTextSize(80);
        comboPaint.setTextAlign(Paint.Align.CENTER);

        explosionPaint = new Paint();
        explosionPaint.setColor(Color.WHITE);
        explosionPaint.setStyle(Paint.Style.FILL);
        explosionPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));

        judgmentPaint = new Paint();
        judgmentPaint.setTextSize(60);
        judgmentPaint.setTextAlign(Paint.Align.CENTER);
        judgmentPaint.setFakeBoldText(true);

        setBackgroundColor(Color.BLACK);
    }

    public void setChart(ChartMania chart) {
        this.chart = chart;
        if (chart != null && chart.getNotas() != null) {
            Log.d(TAG, "setChart: Chart set with " + chart.getNotas().size() + " notes.");
        } else {
            Log.w(TAG, "setChart: Chart or notes are null.");
        }
    }

    public void startGame() {
        Log.d(TAG, "startGame: Game starting and resetting state.");
        
        score = 0;
        displayScore = 0;
        combo = 0;
        maxCombo = 0;
        perfects = 0;
        greats = 0;
        goods = 0;
        misses = 0;

        explosions.clear();
        judgmentTexts.clear();

        if (chart != null && chart.getNotas() != null) {
            activeNotes = new ArrayList<>(chart.getNotas());
        }

        if (chart != null) {
            heldNotes = new NotaMania[chart.getNumPistas() + 1];
            laneHeld.clear();
        }

        startTime = System.currentTimeMillis();
        isPlaying = true;
        invalidate();
    }

    public void stopGame() {
        isPlaying = false;
    }
    
    public void pauseGame() {
        if (isPlaying) {
            isPlaying = false;
            pauseTime = System.currentTimeMillis();
        }
    }

    public void resumeGame() {
        if (!isPlaying && pauseTime > 0) {
            long pausedDuration = System.currentTimeMillis() - pauseTime;
            startTime += pausedDuration; 
            pauseTime = 0;
            isPlaying = true;
            invalidate(); 
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (chart == null) return;

        long currentTime = System.currentTimeMillis();
        long gameTime = isPlaying ? currentTime - startTime : pauseTime - startTime;

        drawLanes(canvas);
        drawHitLine(canvas, currentTime);
        drawNotes(canvas, gameTime);
        drawExplosions(canvas);
        drawUI(canvas);
        drawJudgmentTexts(canvas);

        if (isPlaying) {
            checkMisses(gameTime);
            invalidate();
        }
    }

    private void drawUI(Canvas canvas) {
        if (displayScore < score) {
            displayScore += Math.max(1, (score - displayScore) / 8);
        }
        canvas.drawText(String.format("%07d", displayScore), getWidth() / 2, 150, scorePaint);

        if (combo > 2) {
            canvas.drawText(String.valueOf(combo), getWidth() / 2, getHeight() / 2, comboPaint);
        }
    }

    private void drawLanes(Canvas canvas) {
        int numLanes = chart.getNumPistas();
        if (numLanes <= 0) return;
        int viewWidth = getWidth();
        int laneWidth = viewWidth / numLanes;
        for (int i = 1; i < numLanes; i++) {
            int x = i * laneWidth;
            canvas.drawLine(x, 0, x, getHeight(), lanePaint);
        }
    }

    private void drawHitLine(Canvas canvas, long currentTime) {
        double breathingCycle = (currentTime % 2000) / 2000.0;
        float breath = (float) Math.sin(breathingCycle * 2 * Math.PI);
        int breathingAlpha = 80 + (int) (100 * ((breath + 1) / 2f));

        int finalAlpha = breathingAlpha;
        float finalStrokeWidth = 5f;
        hitLinePaint.setColor(Color.CYAN);

        long pulseElapsed = currentTime - hitlinePulseTime;
        if (hitlinePulseTime != -1 && pulseElapsed < 200) {
            float progress = pulseElapsed / 200f;
            finalAlpha = (int) (255 * (1 - progress));
            finalStrokeWidth = 5 + (10 * (1 - progress));
        }

        hitLinePaint.setAlpha(finalAlpha);
        hitLinePaint.setStrokeWidth(finalStrokeWidth);

        float y = getHeight() * HITLINE_POSITION_Y_PERCENT;
        canvas.drawLine(0, y, getWidth(), y, hitLinePaint);
    }

    private void drawNotes(Canvas canvas, long currentTime) {
        if (activeNotes == null) return;
        int numLanes = chart.getNumPistas();
        int laneWidth = getWidth() / numLanes;
        float hitLineY = getHeight() * HITLINE_POSITION_Y_PERCENT;
        float pixelsPerMillisecond = hitLineY / NOTE_TRAVEL_TIME_MS;

        for (NotaMania nota : activeNotes) {
            float timeToHit = nota.getTiempoMs() - currentTime;
            if (timeToHit < NOTE_TRAVEL_TIME_MS) {
                float y = hitLineY - (timeToHit * pixelsPerMillisecond);
                int laneIndex = nota.getCarril() - 1;
                float left = laneIndex * laneWidth + 10;
                float right = left + laneWidth - 20;

                if (nota.getDuracionMs() != null && nota.getDuracionMs() > 0) {
                    Paint paintToUse = holdNotePaint;
                    if (heldNotes[nota.getCarril()] == nota && laneHeld.get(nota.getCarril())) {
                        paintToUse = holdNoteActivePaint;
                    }
                    float yEnd = y - (nota.getDuracionMs() * pixelsPerMillisecond);
                    RectF noteRect = new RectF(left, yEnd, right, y);
                    canvas.drawRoundRect(noteRect, 20, 20, paintToUse);
                } else {
                    // --- CAMBIO VISUAL: NOTAS MÁS GRUESAS ---
                    float top = y - 15; // Se reduce el valor para hacer la nota más alta (30px en total)
                    float bottom = y + 15;
                    RectF noteRect = new RectF(left, top, right, bottom);
                    canvas.drawRoundRect(noteRect, 10, 10, notePaint); // Se reduce el radio de las esquinas para que sea más rectangular
                }
            }
        }
    }

    private void drawExplosions(Canvas canvas) {
        Iterator<Explosion> iterator = explosions.iterator();
        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();
            explosion.update();
            if (explosion.isFinished()) {
                iterator.remove();
            } else {
                explosionPaint.setAlpha(explosion.alpha);
                canvas.drawCircle(explosion.x, explosion.y, explosion.radius, explosionPaint);
            }
        }
    }

    private void drawJudgmentTexts(Canvas canvas) {
        Iterator<JudgmentText> iterator = judgmentTexts.iterator();
        while (iterator.hasNext()) {
            JudgmentText text = iterator.next();
            text.update();
            if (text.isFinished()) {
                iterator.remove();
            } else {
                judgmentPaint.setColor(text.color);
                judgmentPaint.setAlpha(text.alpha);
                canvas.drawText(text.text, text.x, text.y, judgmentPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isPlaying || chart == null) return false;

        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        float x = event.getX(pointerIndex);

        int numLanes = chart.getNumPistas();
        int laneWidth = getWidth() / numLanes;
        int tappedLane = (int) (x / laneWidth) + 1;

        if (tappedLane < 1 || tappedLane > numLanes) return true;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                laneHeld.put(tappedLane, true);
                checkHit(tappedLane, true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                laneHeld.put(tappedLane, false);
                checkHit(tappedLane, false);
                break;
        }
        return true;
    }

    private void checkGameCompletion() {
        if (activeNotes != null && activeNotes.isEmpty()) {
            boolean anyHeld = false;
            for (int i = 0; i < heldNotes.length; i++) {
                if (heldNotes[i] != null) {
                    anyHeld = true;
                    break;
                }
            }
            if (!anyHeld && gameCompletionListener != null) {
                gameCompletionListener.onGameCompleted();
            }
        }
    }

    private void checkHit(int lane, boolean isDown) {
        long hitTime = System.currentTimeMillis() - startTime;

        if (!isDown) {
            NotaMania holdNote = heldNotes[lane];
            if (holdNote != null) {
                long endDelta = Math.abs((holdNote.getTiempoMs() + holdNote.getDuracionMs()) - hitTime);
                if (endDelta <= MISS_THRESHOLD_MS) {
                    score += 300;
                    combo++;
                    soundManager.playSound(SoundManager.SOUND_NOTE_HIT);
                    createExplosion(lane);
                    addJudgmentText("PERFECT");
                }
                heldNotes[lane] = null;
                checkGameCompletion();
                return;
            }
        }

        Iterator<NotaMania> iterator = activeNotes.iterator();
        while (iterator.hasNext()) {
            NotaMania nota = iterator.next();
            if (nota.getCarril() == lane) {
                long delta = Math.abs(nota.getTiempoMs() - hitTime);
                if (delta <= MISS_THRESHOLD_MS) {
                    String judgment;
                    if (delta <= PERFECT_WINDOW_MS) {
                        score += 300;
                        perfects++;
                        judgment = "PERFECT";
                    } else if (delta <= GREAT_WINDOW_MS) {
                        score += 100;
                        greats++;
                        judgment = "GREAT";
                    } else {
                        score += 50;
                        goods++;
                        judgment = "GOOD";
                    }
                    combo++;
                    if (combo > maxCombo) maxCombo = combo;
                    soundManager.playSound(SoundManager.SOUND_NOTE_HIT);
                    createExplosion(lane);
                    addJudgmentText(judgment);
                    hitlinePulseTime = System.currentTimeMillis();

                    if (nota.getDuracionMs() != null && nota.getDuracionMs() > 0) {
                        heldNotes[lane] = nota;
                    } else {
                        iterator.remove();
                    }
                    checkGameCompletion();
                    return;
                }
            }
        }
    }

    private void checkMisses(long currentTime) {
        if (activeNotes == null) return;
        Iterator<NotaMania> iterator = activeNotes.iterator();
        while (iterator.hasNext()) {
            NotaMania nota = iterator.next();
            long endTime = nota.getTiempoMs() + (nota.getDuracionMs() != null ? nota.getDuracionMs() : 0);
            if (currentTime > endTime + MISS_THRESHOLD_MS) {
                if (heldNotes[nota.getCarril()] != nota) {
                    combo = 0;
                    misses++;
                    addJudgmentText("MISS");
                    iterator.remove();
                    checkGameCompletion();
                }
            }
        }
    }

    private void addJudgmentText(String text) {
        int color;
        switch (text) {
            case "PERFECT": color = Color.CYAN; break;
            case "GREAT": color = Color.GREEN; break;
            case "GOOD": color = Color.YELLOW; break;
            default: color = Color.GRAY; break; // MISS
        }
        judgmentTexts.add(new JudgmentText(text, getWidth() / 2f, getHeight() * 0.75f, color));
    }

    public int getScore() { return score; }
    public int getMaxCombo() { return maxCombo; }
    public int getPerfects() { return perfects; }
    public int getGreats() { return greats; }
    public int getGoods() { return goods; }
    public int getMisses() { return misses; }

    private void createExplosion(int lane) {
        int numLanes = chart.getNumPistas();
        int laneWidth = getWidth() / numLanes;
        float x = (lane - 0.5f) * laneWidth;
        float y = getHeight() * HITLINE_POSITION_Y_PERCENT;
        explosions.add(new Explosion(x, y));
    }

    private static class Explosion {
        float x, y, radius;
        int alpha;
        private final long duration = 200;
        private final long startTime;

        Explosion(float x, float y) {
            this.x = x;
            this.y = y;
            this.radius = 0;
            this.alpha = 255;
            this.startTime = System.currentTimeMillis();
        }

        void update() {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = (float) elapsed / duration;
            if (progress > 1) progress = 1;
            radius = 150 * progress;
            alpha = (int) (255 * (1 - progress));
        }

        boolean isFinished() {
            return alpha <= 0;
        }
    }

    private static class JudgmentText {
        String text;
        float x, y;
        int color, alpha;
        private final long duration = 600;
        private final long startTime;

        JudgmentText(String text, float x, float y, int color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
            this.alpha = 255;
            this.startTime = System.currentTimeMillis();
        }

        void update() {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = (float) elapsed / duration;
            if (progress > 1) progress = 1;
            y -= progress * 2;
            alpha = (int) (255 * (1 - progress));
        }

        boolean isFinished() {
            return alpha <= 0;
        }
    }
}
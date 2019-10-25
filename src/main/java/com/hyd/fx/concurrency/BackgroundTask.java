package com.hyd.fx.concurrency;

import com.hyd.fx.dialog.AlertDialog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

import static com.hyd.fx.app.AppThread.runUIThread;

/**
 * 后台任务，与 Service 不同的是：
 * 1、更轻量级，不可撤销；
 * 2、加上 onTaskFinish，不论是否成功都会执行
 */
public class BackgroundTask {

    public static final Consumer<Throwable> DEFAULT_ON_TASK_FAIL = e -> AlertDialog.error("错误", e);

    private Runnable task;

    private Runnable onTaskBeforeRun;

    private Runnable onTaskSuccess;

    private Consumer<Throwable> onTaskFail = DEFAULT_ON_TASK_FAIL;

    private Runnable onTaskFinish;

    private ExecutorService pool = ForkJoinPool.commonPool();

    public static BackgroundTask runTask(Runnable task) {
        return new BackgroundTask(task);
    }

    private BackgroundTask(Runnable task) {
        this.task = task;
    }

    public BackgroundTask(
        Runnable task, Runnable onTaskBeforeRun, Runnable onTaskSuccess,
        Consumer<Throwable> onTaskFail, Runnable onTaskFinish, ExecutorService pool) {

        this.task = task;
        this.onTaskBeforeRun = onTaskBeforeRun;
        this.onTaskSuccess = onTaskSuccess;
        if (onTaskFail != null) {
            this.onTaskFail = onTaskFail;
        }
        this.onTaskFinish = onTaskFinish;
        if (pool != null) {
            this.pool = pool;
        }
    }

    public BackgroundTask whenBeforeStart(Runnable runnable) {
        this.onTaskBeforeRun = runnable;
        return this;
    }

    public BackgroundTask whenTaskSuccess(Runnable runnable) {
        this.onTaskSuccess = runnable;
        return this;
    }

    public BackgroundTask whenTaskFail(Consumer<Throwable> consumer) {
        this.onTaskFail = consumer;
        return this;
    }

    public BackgroundTask whenTaskFinish(Runnable runnable) {
        this.onTaskFinish = runnable;
        return this;
    }

    public BackgroundTask withPool(ExecutorService executorService) {
        if (executorService != null) {
            this.pool = executorService;
        }
        return this;
    }

    /**
     * 启动后台任务执行。应该在界面线程中调用这个方法。
     */
    public void start() {
        if (onTaskBeforeRun != null) {
            onTaskBeforeRun.run();
        }

        pool.execute(() -> {
            try {
                try {
                    if (task != null) {
                        task.run();
                    }
                    if (onTaskSuccess != null) {
                        runUIThread(onTaskSuccess);
                    }
                } catch (Throwable e) {
                    if (onTaskFail != null) {
                        runUIThread(() -> onTaskFail.accept(e));
                    }
                } finally {
                    if (onTaskFinish != null) {
                        runUIThread(onTaskFinish);
                    }
                }
            } catch (Throwable e) {
                DEFAULT_ON_TASK_FAIL.accept(e);
            }
        });
    }
}

package com.raulvintila.app.lieflashcards.Communication;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import android.app.Activity;

import com.raulvintila.app.lieflashcards.AsyncSynchronize;


public class TaskHelper
{
    public final WeakHashMap<String, WeakReference<AsyncSynchronize>> tasks = new WeakHashMap<String, WeakReference<AsyncSynchronize>>();
    private static TaskHelper instance;

    public static TaskHelper getInstance()
    {
        if (instance == null)
        {
            synchronized (TaskHelper.class)
            {
                if (instance == null)
                {
                    instance = new TaskHelper();
                }
            }
        }

        return instance;
    }

    /**
     * Gets the task from the map of tasks
     * @param key The key of the task
     * @return The task, or null
     */
    public AsyncSynchronize getTask(String key)
    {
        return tasks.get(key) == null ? null : tasks.get(key).get();
    }

    /**
     * Adds a new task to the map
     * @param key The key
     * @param response The task
     */
    public void addTask(String key, AsyncSynchronize response)
    {
        addTask(key, response, null);
    }

    /**
     * Adds a new task to the map and attaches the activity to it
     * @param key The key
     * @param response The task
     * @param o The activity
     */
    public void addTask(String key, AsyncSynchronize response, Activity o)
    {
        detach(key);
        tasks.put(key, new WeakReference<AsyncSynchronize>(response));

        if (o != null)
        {
            attach(key, o);
        }
    }

    /**
     * Removes and detaches the activty from a task
     * @param key
     */
    public void removeTask(String key)
    {
        detach(key);
        tasks.remove(key);
    }

    /**
     * Detaches the activity from a task if it's still available
     * @param key
     */
    public void detach(String key)
    {
        if (tasks.containsKey(key) && tasks.get(key) != null && tasks.get(key).get() != null)
        {
            tasks.get(key).get().detach();
        }
    }

    /**
     * Attaches an activity to a task if its available
     * @param key The key
     * @param o The activity
     */
    public void attach(String key, Activity o)
    {
        AsyncSynchronize handler = getTask(key);
        if (handler != null)
        {
            handler.attach(o);
        }
    }
}
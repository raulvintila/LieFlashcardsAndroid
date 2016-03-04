package com.raulvintila.app.lieflashcards;


public class PcCard
{
    private String name;
    private String front;
    private String back;
    private String backType;
    private String frontType;

    public PcCard(String name, String front, String back, String backType, String frontType)
    {
        this.name = name;
        this.front = front;
        this.back = back;
        this.backType = backType;
        this.frontType = frontType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFront()
    {
        return front;
    }

    public void setFront(String front)
    {
        this.front = front;
    }

    public String getBack()
    {
        return back;
    }

    public void setBack(String back)
    {
        this.back = back;
    }

    public String getBackType()
    {
        return backType;
    }

    public void setBackType(String backType)
    {
        this.backType = backType;
    }

    public String getFrontType()
    {
        return frontType;
    }

    public void setFrontType(String frontType)
    {
        this.frontType = frontType;
    }
}

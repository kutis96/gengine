/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gengine.logic.resman;

import gengine.logic.callback.Callback;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface ResourceManager {
    
    public void addToQueue(GengineResourceDescriptor res);
    
    public void clearQueue();
    
    public void loadAll();
    
    public GengineResource accessResource(GengineResourceDescriptor res);
    
    public Object getLoadingProgress();
    
    public boolean isLoaded();
    
    public boolean isLoading();
    
    public String[] getErrors();

}

# CustomExoPlayer

    After adding this library you can play media file from network or local in a very easy manner.  
	This library supports live streaming media file like Dash Streaming (.mpd file) ,Hls Streaming (.m3u8 file)
	and Smooth Streaming (.ism file).
	
	
	
	
Adding this view in your layout and find this view in your activity.


    <rahul.agrahari.customexoplayer.view.PlayerView
        android:id="@+id/customPlayerView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />
		
		
		
		
Just make the request for playing media file in a different manner.

       For Default Play media files.
	   
	   
		   player = new Player.Builder(MainActivity.this, "Enter the media file url", handler)
					.setPlayerLoaderListener(playerView.getPlayerloaderlistener())
					.setAppName("Enter the app name")
					.setDataSourceTransfer(playerView.getDataSourceTransfer())
					.build();
					
					
					
					
Play Media files with any ads.



			player = new Player.Builder(MainActivity.this, "Enter the media file url", handler)
							.setPlayerLoaderListener(playerView.getPlayerloaderlistener())
							.setAdsListener(playerView.getAdsloaderlistener())
							.setAppName("Enter the app name")
							.isShowAds(true, playerView.getmPlayAds())
							.setDataSourceTransfer(playerView.getDataSourceTransfer())
							.setAdsUrl("Enter the ads media file url")
							.build();
							
							
							
							
							
Play Media files in looping.

		 for infinitely play just pass 0 in setMediaLoop(true,0).
		 
		 
			player = new Player.Builder(MainActivity.this, "Enter the media file url", handler)
					.setMediaLoop(true, "Enter the count") 
					.setAppName("Enter the app name")
					.setPlayerLoaderListener(playerView.getPlayerloaderlistener())
					.setDataSourceTransfer(playerView.getDataSourceTransfer())
					.build();
					
					
					
					
					
Play media files in sequentially 


			player = new Player.Builder(MainActivity.this, "", handler)
							.setConcatenateUrl("Enter the string array of media files")
							.setPlayerLoaderListener(playerView.getPlayerloaderlistener())
							.setAppName("Enter the app name")
							.setDataSourceTransfer(playerView.getDataSourceTransfer())
							.build();
							
							
							

Start to play media files just call this method.


    playerView.setPlayer(player);
	
	
	pass true if you play media with ads otherwise pass false.
	
	
    player.prepareMedia(true);							
       
            		
	   
					  
					  
	  

# PopMovie
This project as a small RESTful service, it gets movie records from api.themoviedb.org
Two fragments on MainActivity. One display fetched movie list (recycleview), once you click the 
any movie, it will switch to another fragment to display the detail of the movie.

This project uses viewBinding for views, Glide for loading images, coroutine to load
data from network, Gson for converting between json to object.

Further works:
There are a lot things can be added in, such SqlLite (Room), logging. We can also add scrolling listener on the recycleView,
so that when it hit the buttom of the data list, it will fetch the next page.

Failure:
This project failed on loading the movies. It got 404 error. I think that I did not figure out the path for this new
webservice.

I feel that I do not have enough time to finish this project for 1 and half days.

# PopMovie (pagination)
This project as a small RESTful service, it gets 20 movie records per page from api.themoviedb.org. 
One display fetched movie list (recycleview). When the recycleview reach the end of the list, 
it will fetch the next page.

This project uses viewBinding for views, Glide for loading images, coroutine to load
data from network, Gson for converting between json to object, pagination for big list data.

Further works:
There are a lot things can be added in, such SqlLite (Room), logging. We can also add scrolling listener on the recycleView,
so that when it hit the buttom of the data list, it will fetch the next page.

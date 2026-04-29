import { Link } from "react-router-dom";
function Home() {
  return (
    <div className="container-fluid min-vh-100 d-flex justify-content-center align-items-center bg-light">
      <div className="text-center">
        <h1 className="mb-4">E famo sta Fetch</h1>
        <div
          className="tenor-gif-embed"
          data-postid="18184486"
          data-share-method="host"
          data-aspect-ratio="1.33333"
          data-width="100%">
          <a href="https://tenor.com/view/go-lift-off-gif-18184486">
            Go Lift Off GIF
          </a>
          from <a href="https://tenor.com/search/go-gifs">Go GIFs</a>
        </div>{" "}
        <script
          type="text/javascript"
          async
          src="https://tenor.com/embed.js"></script>
        <div className="d-flex flex-column gap-3 mt-3">
          <Link to="/get" className="btn btn-light border">
            GET
          </Link>

          <Link to="/post" className="btn btn-light border">
            POST
          </Link>

          <Link to="/registration" className="btn btn-light border">
            REGISTRATI
          </Link>
        </div>
      </div>
    </div>
  );
}
export default Home;

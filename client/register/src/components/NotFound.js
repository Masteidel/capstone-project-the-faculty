import {Link} from "react-router-dom";

function NotFound() {
    return (
        <div className="not-found-container">
            <h1 className="not-found-title">404</h1>
            <p className="not-found-subtitle">Oops! The page you're looking for doesn't exist.</p>
            <p className="not-found-description">
                It seems like the page you are trying to reach has been moved, deleted, or never existed. Here's some random fun instead!
            </p>
            <iframe
                src="https://giphy.com/embed/l2JehQ2GitHGdVG9y"
                width="480"
                height="270"
                frameBorder="0"
                allowFullScreen
                title="fun-gif">
            </iframe>
            <Link to="/" className="not-found-home-link">Go to Homepage</Link>
        </div>
    );
}

export default NotFound;
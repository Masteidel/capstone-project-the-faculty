import { Link } from "react-router-dom";
import './Intro.css';

function Intro() {
    return (
        <div className="intro-container">
            {/* Hero Section */}
            <section className="hero-section">
                <div className="hero-text">
                    <h1 className="hero-title">
                        The Future of Tech <span className="highlight">Is Diverse.</span>
                    </h1>
                    <p className="hero-subtitle">Bridging Training, Technology, and Diversity Gaps</p>
                    <p className="hero-description">
                        We enable businesses and emerging talent to thrive through custom programs designed for specific client outcomes.
                    </p>
                    <div className="hero-buttons">
                        {/* Button for Applicant Login */}
                        <Link className="nav-btn nav-spacing" to={`/login/Professor`}>Professor Login</Link>
                        
                        {/* Button for Partner Login */}
                        <Link className="nav-btn nav-btn-outline" to={`/login/Student`}>Student Login</Link>
                    </div>
                </div>
                {/* Placeholder for hero image or illustration */}
                <div className="hero-image">
                    {/* You can insert an image or a graphic here */}
                </div>
            </section>

            {/* About Us Section */}
            <section className="about-section">
                <h2>About Us</h2>
                <p>
                    We are a team of passionate professionals dedicated to advancing the future of tech by building inclusive and diverse talent pipelines. 
                    Our mission is to empower individuals with the skills and opportunities they need to thrive in an ever-evolving tech landscape.
                </p>
            </section>
        </div>
    );
}

export default Intro;

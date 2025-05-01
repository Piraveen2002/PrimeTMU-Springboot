import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../App.css";
import axios from 'axios';

function MainFeed() {
    const products = [];
    const services = [];
    const [posts, setPosts] = useState([]);


    useEffect(() => {
        axios.get('http://localhost:8080/api/posts')
            .then(response => setPosts(response.data))
            .catch(error => {
                console.error("Axios error fetching posts:", error);
            });
    }, []);

    posts.map(post => 
        {
            if (post.category == "product")
                products.push(post);
            else
                services.push(post);
        })
    return (
        <div className="post-container">
            <div className="product-container">
                <h2>Products</h2>
                {products.map((post, index) => (
                    <div className="post" key={index}>
                        <h4>{`${post.title}`}</h4>
                    </div>
                ))}
            </div>
        
            <div className="service-container">
                <h2>Services</h2>
                {services.map((post, index) => (
                    <div className="post-box" key={index}>
                        <h4>{`${post.title}`}</h4>
                    </div>
                ))}
            </div>
        </div>

    )
}

export default MainFeed;
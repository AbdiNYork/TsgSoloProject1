import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import {GoogleOAuthProvider} from "@react-oauth/google";
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root')).render(
    <GoogleOAuthProvider clientId="240996260371-jiqfh066uj0suu9ajvhrrbc1mp2le583.apps.googleusercontent.com">

       <BrowserRouter>
            <App />
       </BrowserRouter>
    </GoogleOAuthProvider>

)

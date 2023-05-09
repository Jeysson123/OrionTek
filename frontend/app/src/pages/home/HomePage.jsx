
import {useEffect, useState} from "react";
import './styles.css';
import AddClient from "../../components/client-component/AddClient";
import Client from "../../components/client-component/Client";


const Homepage = () => {

    const [clients, setClients] = useState([]);


    useEffect(() => {
        async function getClientsAsync() {
            const apiResponse = await fetch('http://localhost:8080/api/clients');
            const result = await apiResponse.json();
            const parsedClients = result.map(item => ({id: item.id, name: item.name, enterprise: item.enterprise}));
            setClients(parsedClients);
        }

        getClientsAsync();
    }, []);


    return (

        <div className="container">

            <AddClient></AddClient>

            {clients && clients.length > 0 ? clients.map((item) =>
                    <Client id={item.id} name={item.name} enterprise={item.enterprise} />)
            : null

            }

        </div>


    )
}

export default Homepage;
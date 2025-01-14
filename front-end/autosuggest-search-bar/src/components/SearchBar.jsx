import { useEffect, useState } from "react";
import { CiSearch } from "react-icons/ci";
import { IoIosClose } from "react-icons/io";
import { useDebounce } from "../hooks/useDebounce";
import { TfiViewList } from "react-icons/tfi";
import { BsArrowReturnLeft } from "react-icons/bs";
const SearchBar = () => {
    const [searchInput,setSearchInput] = useState("");
    const debouncedSearch = useDebounce(searchInput, 500);
    const [result,setResult] = useState({});

    console.log(result)

    useEffect(()=>{
      if(debouncedSearch.trim()){
        const loadSearchResult = async () => {
            const response = await fetch(
            `http://search-service:8080/autocomplete?searchTerm=${debouncedSearch}`
            );
        if (response.ok) {
            const data = await response.json();  // Parse the JSON response
            setResult(data);        // Store the data in result
          } else {
            console.error('Failed to fetch data');
          }
        }
        loadSearchResult();
      }else{
        setResult({});
      }
    },[debouncedSearch])

    const underlineMatch = (text, searchTerm) => {
        if (!searchTerm) return text; // No underline if no search term
      
        const regex = new RegExp(`(${searchTerm})`, 'gi'); // Create a regex to find the search term (case-insensitive)
        const parts = text.split(regex); // Split the text into matching and non-matching parts
      
        return parts.map((part, index) =>
          regex.test(part) ? (
            <span key={index} style={{ textDecoration: 'underline'}}>{part}</span>
          ) : part
        );
    };

    const renderSearchResult = (item, searchInput) => {
        switch (item.type) {
          case 'SIMILAR_MATCH':
            return (
                <li className="flex flex-row items-center p-1 px-4 justify-between hover:bg-blue-500 hover:cursor-pointer hover:text-black rounded-md" key={item.id}>
                    <div className="flex flex-row items-center gap-2">
                        <TfiViewList />
                        <span>{item.suggestion}</span>
                    </div>
                <BsArrowReturnLeft/>
            </li>
            );
            
          case 'BRAND_MATCH':
            return (
                <li className="flex flex-row items-center p-1 px-4 justify-between hover:bg-blue-500 hover:cursor-pointer hover:text-black rounded-md" key={item.id}>
                <div className="flex flex-row items-center gap-2">
                    <TfiViewList />
                    <div className="flex flex-col">
                        <span className="text-slate-400">{underlineMatch(item.brandName, searchInput)}</span>
                        <span>{item.suggestion}</span>
                    </div>
                </div>
                <BsArrowReturnLeft/>
            </li>
            );
            
          case 'PRODUCT_MATCH':
            return (
                <li className="flex flex-row items-center p-1 px-4 justify-between hover:bg-blue-500 hover:cursor-pointer hover:text-black rounded-md" key={item.id}>
                    <div className="flex flex-row items-center gap-2">
                        <TfiViewList />
                        <span>{underlineMatch(item.suggestion, searchInput)}</span>
                    </div>
                <BsArrowReturnLeft/>
            </li>
            );

            case 'DESCRIPTION_MATCH':
                return (
                    <li className="flex flex-row items-center p-1 px-4 justify-between hover:bg-blue-500 hover:cursor-pointer hover:text-black rounded-md" key={item.id}>
                    <div className="flex flex-row items-center gap-2">
                        <TfiViewList />
                        <div className="flex flex-col">
                            <span className="text-slate-400 text-sm">...{underlineMatch(item.description, searchInput)}...</span>
                            <span>{item.suggestion}</span>
                        </div>
                    </div>
                    <BsArrowReturnLeft/>
                </li>
                );
          default:
            return (
              <div className="flex flex-row items-center gap-2">
                <TfiViewList />
                <span>{item.suggestion}</span>
              </div>
            );
        }
      };
    // const navigate = useNavigate();
    
    // const buttonClickHandler = () =>{    
    //     // Navigate to RecipeSearch component
    //     navigate('/search');
        
    // }
    
    return(
        <div className="relative flex flex-col mt-10 py-2 px-6 mx-5 rounded-md bg-gray-50 border flex w-full">
            {/* SEARCH BAR*/}
            <div className="absolute inset-0 flex mt-10 py-2 px-6 mx-5 rounded-full bg-gray-50 border flex h-[50px] w-fill">
                <div className="flex flex-row items-center justify-center pr-2">
                    <CiSearch />
                </div>
                <input
                value={searchInput}
                onChange={(e)=>setSearchInput(e.target.value)}
                type="text" 
                placeholder="Type to search" 
                className="bg-transparent w-full focus:outline-none pr-4 text-sm border-0 text-slate-700" 
            />
                <div className="flex flex-row items-center justify-center px-2 "
                >
                    <IoIosClose />
                </div>
            </div>
            {/* SEARCH BAR*/}
            <div className="text-left text-slate-500 py-2 px-6 mt-[100px] max-h-[300px] overflow-y-scroll">
            {Object.entries(result).length > 0 ? 
            Object.entries(result).map(([key, items]) => (
                <div key={key} className="result-section">
                    <h2 className="text-black">{key.replace("_"," ")}</h2>
                    <ul>
                        {items.map((item) => (
                            <div key={item.id}>
                                {renderSearchResult(item, searchInput)}
                            </div>
                        
                        ))}
                    </ul>
                </div>)):
                <>
                <p className="mt-12 text-center">No search result found.</p>
                </>}
            </div>
        </div>
        
    )
}
export default SearchBar;
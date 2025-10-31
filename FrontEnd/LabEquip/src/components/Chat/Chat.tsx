"use client";

import { useState } from 'react';
import styles from './Chat.module.css';
import ReactMarkdown from "react-markdown";

interface Message {
    type: 'user' | 'bot';
    content: string;
}

export default function Chat({ onClose }: { onClose?: () => void }) {
    const [messages, setMessages] = useState<Message[]>([]);
    const [input, setInput] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!input.trim()) return;

        const userMessage = { type: 'user' as const, content: input };
        setMessages(prev => [...prev, userMessage]);
        setInput('');
        setIsLoading(true);

        try {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/chatbot/query`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ query: input }),
            });

            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.error || 'Failed to get response');
            }

            setMessages(prev => [...prev, { type: 'bot', content: data.response }]);
        } catch (error) {
            console.error('Error:', error);
            setMessages(prev => [...prev, { 
                type: 'bot', 
                content: 'Sorry, I encountered an error. Please try again.' 
            }]);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className={styles.chatContainer}>
            <div className={styles.panelHeader}>
                <div className={styles.title}>LabEquip Chat</div>
                <button aria-label="Close chat" className={styles.closeButton} onClick={() => onClose && onClose()}>
                    ×
                </button>
            </div>
            <div className={styles.messagesContainer}>
                {messages.map((message, index) => (
                    <div 
                        key={index} 
                        className={`${styles.message} ${
                            message.type === 'user' ? styles.userMessage : styles.botMessage
                        }`}
                    >
                        <ReactMarkdown
                         
                         >
                            {message.content}
                            </ReactMarkdown>
                        
                    </div>
                ))}
                {isLoading && (
                    <div className={`${styles.message} ${styles.botMessage}`}>
                        Thinking...
                    </div>
                )}
            </div>
            <form onSubmit={handleSubmit} className={styles.inputContainer}>
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    placeholder="Ask me anything..."
                    className={styles.input}
                    disabled={isLoading}
                />
                <button 
                    type="submit" 
                    className={styles.sendButton}
                    disabled={isLoading || !input.trim()}
                >
                    Send
                </button>
            </form>
        </div>
    );
}
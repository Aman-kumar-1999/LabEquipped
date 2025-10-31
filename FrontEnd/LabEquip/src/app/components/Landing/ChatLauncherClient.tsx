"use client";

import { useState } from 'react';
import dynamic from 'next/dynamic';
import styles from './ChatLauncher.module.css';

const Chat = dynamic(() => import('@/components/Chat/Chat'), { ssr: false });

export default function ChatLauncherClient() {
  const [open, setOpen] = useState(false);

  return (
    <>
      {/* Chat panel */}
      {open && (
        <div className={styles.panelWrapper} role="dialog" aria-modal="true">
          <div className={styles.panel}>
            <Chat onClose={() => setOpen(false)} />
          </div>
        </div>
      )}

      {/* Sticky button */}
      <button
        className={styles.stickyButton}
        aria-label="Open chat"
        onClick={() => setOpen((v) => !v)}
        title={open ? 'Close chat' : 'Open chat'}
      >
        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v10z" stroke="white" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
          <path d="M7 10h10M7 14h6" stroke="white" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
      </button>
    </>
  );
}
